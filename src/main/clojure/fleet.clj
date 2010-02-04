(ns fleet
  (:import
    [java.io File]
    [java.util.regex Pattern]
    [clojure.lang IFn])
  (:require
    [clojure.contrib.lazy-xml :as lx])
  (:use
    [clojure.contrib def]
    [clojure.template]
    [fleet parser builder loader util runtime]))

;;;;;;;;;; single template ;;;;;;;;;;

(defvar- escape-fn
  (memoize (fn [escape]
    (condp = escape
      :bypass bypass
      :str escape-string
      :clj-str escape-clj-string
      :xml lx/escape-xml
      :regex escape-regex
      escape))))

(defvar- default-opts {
  :escaping  :bypass
  :file-name "FLEET_TEMPLATE"
  :file-path nil
  })

(defn -fleet
  [args template-str options]
  (let [opts (merge default-opts options)]
    (partial
      (load-fleet-string
        (build args (parse template-str))
        (opts :file-path) (opts :file-name))
      (make-runtime (escape-fn (opts :escaping))))))

(defmacro fleet
  "Creates anonymous function from template containing in template-str."
  ([args template-str]
    `(-fleet '~args ~template-str {}))
  ([args template-str options]
    `(-fleet '~args ~template-str ~options)))

;;;;;;;;;; template namespace ;;;;;;;;;;

(defn- filemask-fn
  [type]
  (let [ext (str (if type (str "." type) "") ".fleet")]
    #(.. % getName (endsWith ext))))

(defvar- filter-fn
  (memoize (fn [filter]
    (condp = filter
      :all (constantly true)
      :fleet (filemask-fn nil)
      (condp instance? filter
        String (filemask-fn filter)
        IFn filter
        Pattern #(re-matches filter (.getName %)))))))

(defvar- build-ns
  (memoize (fn [prefix name]
    (symbol (if (empty? (str name)) (str prefix) (str prefix "." name))))))

(defn- def-path-vars
  [root-ns root-path filter escape]
  (let [tpl-infos (make-tpl-infos root-path (filter-fn filter))
        full-ns (partial build-ns root-ns)]
    (doseq [ns (distinct (map :ns tpl-infos))]
      (create-ns (full-ns ns))
      (within-ns (full-ns ns)
        (clojure.core/refer-clojure)
        (use 'fleet)))
    (doseq [{:keys [ns names]} tpl-infos, n names]
      (intern (full-ns ns) n nil))))

(defn- assign-path-templates
  [root-ns root-path filter escape]
  (let [tpl-infos (make-tpl-infos root-path (filter-fn filter))]
    (doseq [{:keys [ns names content file-path file-name]} tpl-infos]
      (let [ns (build-ns root-ns ns)
            arg-names [(last names) 'data]
            opts {:escaping escape, :file-path file-path, :file-name file-name}
            tpl (within-ns ns (-fleet arg-names content opts))
            wrapper (fn ([] (tpl nil nil)) ([a] (tpl a a)) ([a data] (tpl a data)))]
        (doseq [n names]
          (when-not @(ns-resolve ns n)
            (intern ns n wrapper)))))))

(defn -fleet-ns
  [root-ns root-path filters]
  (when-not (even? (count filters))
    (throw (IllegalArgumentException. "fleet-ns requires an even number of forms in filters vector")))
  (do-template [f]
    (doseq [[filter escape] (partition 2 filters)]
      (f root-ns root-path filter escape))
    def-path-vars assign-path-templates))

(defmacro fleet-ns
  "Treats root-path as root of template namespaceand creates template
  function for each file in it with name corresponding to relative path."
  ([root-ns root-path]
    `(-fleet-ns '~root-ns ~root-path [:fleet :bypass]))
  ([root-ns root-path filters]
    `(-fleet-ns '~root-ns ~root-path ~filters)))

