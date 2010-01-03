(ns ru.flamefork.fleet
  (:use [ru.flamefork.fleet.parser]))

(defn process
  "Compile template from AST"
  [ast]
  ; TODO
  ast)

(defn fleet
  "Creates anonymous function from template containing in template-str"
  [template-str]
  (-> template-str parse process))
