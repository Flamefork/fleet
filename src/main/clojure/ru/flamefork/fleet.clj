(ns ru.flamefork.fleet
  (:use [ru.flamefork.fleet.tokenizer]))

(defn process
  "Compile template from AST"
  [ast]
  ; TODO
  ast)

(defn fleet
  "Creates anonymous function from template containing in template-str"
  [template-str]
  (-> template-str tokenize process))
