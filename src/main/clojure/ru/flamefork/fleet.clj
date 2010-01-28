(ns ru.flamefork.fleet
  (:import
    [java.io File]
    [java.util.regex Pattern])
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
      :bypass (fn [s] s)
      :str escape-string
      :xml lx/escape-xml
      escape))))

(defn fleet-
  ([args template-str]
    (fleet- args template-str :bypass))
  ([args template-str escape]
    (partial (eval (build args (parse template-str))) (escape-fn escape))))

(defmacro fleet
  "Creates anonymous function from template containing in template-str."
  [args & more]
  `(fleet- '~args ~@more))

;;;;;;;;;; template namespace ;;;;;;;;;;

(defn- default-file-filter
  [f]
  (and
    (.. f getName (endsWith ".fleet"))
    (.isFile f)
    (not (.isHidden f))))

(defn fleet-ns
  "Treats root-path as root of template namespaceand creates template
  function for each file in it with name corresponding to relative path."
  [root-path]
  (let [tpl-infos (make-tpl-infos root-path default-file-filter)]
    ; Create namespaces and vars
    (doseq [{:keys [ns names]} tpl-infos]
      (create-ns (symbol ns))
      (doseq [n names]
        (eval `(do (ns ~ns) (def ~n)))))
    ; Bind vars to templates
    (doseq [{:keys [ns names source]} tpl-infos]
      (let [arg-names [(last names) 'data]
            tpl (fleet- arg-names source :bypass)
            wrapper (fn ([a] (tpl a a)) ([a data] (tpl a data)))]
        (doseq [n names]
          (eval `(do (ns ~ns) (def ~n ~wrapper))))))))