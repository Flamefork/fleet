(ns ru.flamefork.fleet
  (:import
    [java.io File]
    [java.util.regex Pattern]
    [clojure.lang IFn])
  (:require
    [clojure.contrib.str-utils2 :as su]
    [clojure.contrib.lazy-xml :as lx])
  (:use
    [clojure.contrib.def]
    [ru.flamefork.fleet parser builder loader util]))

;;;;;;;;;; single template ;;;;;;;;;;

(defvar- escape-fn
  (memoize (fn [escape]
    (condp = escape
      :bypass bypass
      :str escape-string
      :clj-str escape-clj-string
      :xml lx/escape-xml
      escape))))

(defn fleet-
  ([args template-str]
    (fleet- args template-str :bypass))
  ([args template-str escape]
    (fleet- args template-str escape [nil "FLEET_TEMPLATE"]))
  ([args template-str escape src]
    (partial (load-fleet-string (build args (parse template-str)) src) (escape-fn escape))))

(defmacro fleet
  "Creates anonymous function from template containing in template-str."
  [args & more]
  `(fleet- '~args ~@more))

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

(defn- def-path-vars
  [root-path filter escape]
  (let [tpl-infos (make-tpl-infos root-path (filter-fn filter))]
    (doseq [{:keys [ns names]} tpl-infos]
      (create-ns (symbol ns))
      (doseq [n names]
        (eval `(do (ns ~ns) (def ~n)))))))

(defn- assign-path-templates
  [root-path filter escape]
  (let [tpl-infos (make-tpl-infos root-path (filter-fn filter))]
    (doseq [{:keys [ns names content src]} tpl-infos]
      (let [arg-names [(last names) 'data]
            tpl (fleet- arg-names content escape src)
            wrapper (fn ([a] (tpl a a)) ([a data] (tpl a data)))]
        (doseq [n names]
          (when-not (.hasRoot (ns-resolve ns n))
            (eval `(do (ns ~ns) (def ~n ~wrapper)))))))))

(defn fleet-ns
  "Treats root-path as root of template namespaceand creates template
  function for each file in it with name corresponding to relative path."
  ([root-path] (fleet-ns root-path [:fleet :bypass]))
  ([root-path filters]
    (when-not (even? (count filters))
      (throw (IllegalArgumentException. "fleet-ns requires an even number of forms in filters vector")))
    (doseq [[filter escape] (partition 2 filters)]
      (def-path-vars root-path filter escape))
    (doseq [[filter escape] (partition 2 filters)]
      (assign-path-templates root-path filter escape))))

