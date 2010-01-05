(ns ru.flamefork.fleet
  (:use
    [ru.flamefork.fleet.loader]
    [ru.flamefork.fleet.parser]
    [ru.flamefork.fleet.compiler]))

(defn fleet
  "Creates anonymous function from template containing in template-str."
  [args template-str]
  (compile-to-fn args (parse template-str)))

(defn- read-template
  [fn-name]
  (slurp (.getPath (find-file (name fn-name)))))

(defmacro deftemplate
  "Creates function with name fn-name and defined args from source.
  Source could be ommited, in this case Fleet will inflect template file name from fn-name."
  ([fn-name args]
    `(deftemplate ~fn-name ~args (@#'ru.flamefork.fleet/read-template '~fn-name)))
  ([fn-name args source]
    `(def ~fn-name (fleet '~args ~source))))
