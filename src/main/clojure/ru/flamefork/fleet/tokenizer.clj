(ns ru.flamefork.fleet.tokenizer
  (:import
    [org.antlr.runtime Token ANTLRStringStream CommonTokenStream]
    [ru.flamefork.fleet FleetLexer FleetParser])
  (:use [clojure.contrib.seq-utils :only (partition-by)]))

(defmulti consume
  "Convert functions for various node types"
  #(.getType %))

(defn children
  "Converts ANTLR Tree to Fleet AST"
  [tree]
  (map consume (.getChildren tree)))

(defmethod consume FleetParser/CHAR
  [token]
  (str (.getText token) (apply str (map #(.getText %) (.getChildren token)))))

(defmethod consume FleetParser/SPACESHIP_OPEN
  [token]
  [:embed (children token)])

(defmethod consume FleetParser/SLIPWAY_OPEN
  [token]
  [:tpl (children token)])

(defn tokenize
  "Builds ANTLR Tree from String"
  [template-str]
  (let [lexer (FleetLexer. (ANTLRStringStream. template-str))
        parser (FleetParser. (CommonTokenStream. lexer))
        tree (.. parser input getTree)]
    (children tree)))

