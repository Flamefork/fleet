(ns fleet.parser
  (:import
    java.util.regex.Pattern)
  (:require
    [clojure.zip :as z]))

;;;;;;;;;; lexer ;;;;;;;;;;

(def #^{:private true} escaped-tokens
    #{"\\)>" "\\\">" "\\<(" "\\<\""})

(def #^{:private true} token-regexs {
  true  (Pattern/compile (str "^(.*?)(\\)>|\">|\\\\\\)>|\\\\\">)") Pattern/DOTALL)
  false (Pattern/compile (str "^(.*?)(<\\(|<\"|\\\\<\\(|\\\\<\")") Pattern/DOTALL)})

(defn- token-seq
  [input]
  (loop [input input, mode false, ast []]
    (let [found (re-find (re-matcher (token-regexs mode) input))
          [pair text token] (or found [input input nil])
          ast (if (empty? text) ast (conj ast text))]
      (if token
        (let [rest (.substring input (.length pair))
              mode (if (escaped-tokens token) mode (not mode))
              ast (conj ast token)]
          (recur rest mode ast))
        ast))))

;;;;;;;;;; parser ;;;;;;;;;;

(defn- unescape-token
  [s]
  (if (escaped-tokens s) (.substring s 1) s))

(def #^{:private true} consumers {
  true {
    ")>"  (fn [_ loc] [false (-> loc z/up z/up)])
    "\">" (fn [_ loc] [false (-> loc (z/append-child [:tpl []]) z/down z/rightmost z/down z/rightmost)])
    :text (fn [t loc] [true  (-> loc (z/append-child [:clj (unescape-token t)]))])}
  false {
    "<\"" (fn [_ loc] [true  (-> loc z/up z/up)])
    "<("  (fn [_ loc] [true  (-> loc (z/append-child [:embed []]) z/down z/rightmost z/down z/rightmost)])
    :text (fn [t loc] [false (-> loc (z/append-child [:text (unescape-token t)]))])}})

(defn- make-ast
  [tokens]
  (loop [tokens (concat ["\">"] tokens ["<\""])
         mode true
         loc (z/vector-zip [])]
    (if (first tokens)
      (let [token (first tokens)
            mc (consumers mode)
            consumer (mc token (:text mc))
            [mode loc] (consumer token loc)]
        (recur (rest tokens) mode loc))
      (first (z/root loc)))))

(defn parse
  "Builds Fleet AST from String"
  [template-str]
  (make-ast (token-seq template-str)))
