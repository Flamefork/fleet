(ns ru.flamefork.fleet
  (:use
    [ru.flamefork.fleet.parser]
    [ru.flamefork.fleet.compiler]))

(defn fleet
  "Creates anonymous function from template containing in template-str."
  [args template-str]
  (compile-to-fn args (parse template-str)))

(defmacro deftemplate
  "Creates function with name fn-name and defined args from source."
  [fn-name args source]
  `(def ~fn-name (fleet '~args ~source)))
