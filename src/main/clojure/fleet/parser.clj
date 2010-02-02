(ns fleet.parser
  (:use
    [clojure.contrib def])
  (:require
    [clojure.zip :as z])
  (:import
    [java.util.regex Pattern]))

;;;;;;;;;; lexer ;;;;;;;;;;

(defvar- token-regexs {
  true  (Pattern/compile (str "^(.*?)(\\)>|\">)") Pattern/DOTALL)
  false (Pattern/compile (str "^(.*?)(<\\(|<\")") Pattern/DOTALL)})

(defn- token-seq
  [input]
  (loop [input input, mode false, ast []]
    (let [found (re-find (re-matcher (token-regexs mode) input))
          [pair text token] (or found [input input nil])
          ast (if (empty? text) ast (conj ast text))]
      (if token
        (recur (.substring input (.length pair)) (not mode) (conj ast token))
        ast))))

;;;;;;;;;; parser ;;;;;;;;;;

(defvar- consumers {
  ")>"  (fn [_ loc] [false (-> loc z/up z/up)])
  "<\"" (fn [_ loc] [true  (-> loc z/up z/up)])
  "<("  (fn [_ loc] [true  (-> loc (z/append-child [:embed []]) z/down z/rightmost z/down z/rightmost)])
  "\">" (fn [_ loc] [false (-> loc (z/append-child [:tpl   []]) z/down z/rightmost z/down z/rightmost)])
  :text {
    true  (fn [token loc] [true  (-> loc (z/append-child [:clj token]))])
    false (fn [token loc] [false (-> loc (z/append-child [:text token]))])}})

(defn- make-ast
  [tokens]
  (loop [tokens (concat ["\">"] tokens ["<\""])
         mode true
         loc (z/vector-zip [])]
    (if (first tokens)
      (let [token (first tokens)
            consumer (consumers token ((consumers :text) mode))
            [mode loc] (consumer token loc)]
        (recur (rest tokens) mode loc))
      (first (z/root loc)))))

(defn parse
  "Builds Fleet AST from String"
  [template-str]
  (make-ast (token-seq template-str)))
