(ns fleet.parser
  (:use
    [clojure.contrib def]
    [fleet.util])
  (:require
    [clojure.contrib.str-utils2 :as su]
    [clojure.zip :as z])
  (:import
    [java.util.regex Pattern]))

;;;;;;;;;; lexer ;;;;;;;;;;

(defn- make-token-regex
  [tokens]
  (Pattern/compile (str "^(.*?)(" (su/join "|" (map escape-regex tokens)) ")") Pattern/DOTALL))

(defvar- token-regexs {
  true (make-token-regex [")>" "\">"])
  false (make-token-regex ["<(" "<\""])})

(defn- token-seq
  [input]
  (loop [input input
         mode false
         ast []]
    (let [found (re-find (re-matcher (token-regexs mode) input))
          [pair text token] (or found [input input nil])
          ast (if (empty? text) ast (conj ast text))]
      (if token
        (recur (.substring input (.length pair)) (not mode) (conj ast token))
        ast))))

;;;;;;;;;; parser ;;;;;;;;;;

(defvar- consumers {
  true {
    ")>"  (fn [token loc] [false (-> loc z/up z/up)])
    "\">" (fn [token loc] [false (-> loc (z/append-child [:tpl []]) z/down z/rightmost z/down z/rightmost)])
    :text (fn [token loc] [true  (-> loc (z/append-child [:clj token]))])
    }
  false {
    "<\"" (fn [token loc] [true  (-> loc z/up z/up)])
    "<("  (fn [token loc] [true  (-> loc (z/append-child [:embed []]) z/down z/rightmost z/down z/rightmost)])
    :text (fn [token loc] [false (-> loc (z/append-child [:text token]))])
    }
  })

(defn- make-ast
  [tokens]
  (loop [tokens (concat ["\">"] tokens ["<\""])
         mode true
         loc (z/vector-zip [])]
    (if (first tokens)
      (let [token (first tokens)
            consumers (consumers mode)
            consumer (consumers token (consumers :text))
            [mode loc] (consumer token loc)]
        (recur (rest tokens) mode loc))
      (first (z/root loc)))))

(defn parse
  "Builds Fleet AST from String"
  [template-str]
  (make-ast (token-seq template-str)))
