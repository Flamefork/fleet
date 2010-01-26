(ns ru.flamefork.fleet
  (:import java.io.File)
  (:require
    [clojure.contrib.str-utils2 :as su])
  (:use
    [clojure.contrib.pprint]
    [ru.flamefork.fleet parser builder loader]))

(defn- default-escape-fn [s] s)

(defn fleet-
  ([args template-str]
    (fleet- args template-str default-escape-fn))
  ([args template-str escape-fn]
    (partial (eval (build args (parse template-str))) escape-fn)))

(defmacro fleet
  "Creates anonymous function from template containing in template-str."
  [args & more]
  `(fleet- '~args ~@more))

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
            tpl (fleet- arg-names source default-escape-fn)
            wrapper (fn ([a] (tpl a a)) ([a data] (tpl a data)))]
        (doseq [n names]
          (eval `(do (ns ~ns) (def ~n ~wrapper))))))))