(ns ru.flamefork.fleet
  (:use
    [ru.flamefork.fleet.parser]
    [ru.flamefork.fleet.compiler]))

(defn fleet
  "Creates anonymous function from template containing in template-str. Args are '(list of args)."
  [args template-str]
  (compile-to-fn args (parse template-str)))
