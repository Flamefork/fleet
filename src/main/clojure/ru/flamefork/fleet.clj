(ns ru.flamefork.fleet
  (:use
    [ru.flamefork.fleet parser builder]))

(defn fleet-
  [args template-str escape-fn]
  (partial (eval (build args (parse template-str))) escape-fn))

(defmacro fleet
  "Creates anonymous function from template containing in template-str."
  [args template-str escape-fn]
  `(fleet- '~args ~template-str ~escape-fn))
