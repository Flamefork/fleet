(ns ru.flamefork.fleet.parser
  (:import
    [org.antlr.runtime Token ANTLRStringStream CommonTokenStream]
    [ru.flamefork.fleet FleetLexer FleetParser])
  (:use [clojure.contrib.seq-utils :only (partition-by)]))

(defstruct node
  :type :children)

(defmulti consume
  "Convert functions for various node types"
  #(.getType %))

(defn children
  "Converts ANTLR Tree to Fleet AST"
  [tree]
  (map consume (.getChildren tree)))

(defmethod consume FleetParser/CHAR
  [token]
  (struct node
    :text
    ; transforming token tree "A->(B C D E)" to node with string ABCDE
    (apply str (map #(.getText %) (cons token (.getChildren token))))))

(defmethod consume FleetParser/SPACESHIP_OPEN
  [token]
  (struct node
    :embed
    (children token)))

(defmethod consume FleetParser/SLIPWAY_OPEN
  [token]
  (struct node
    :tpl
    (children token)))

(defn parse
  "Builds Fleet AST from String"
  [template-str]
  (let [lexer (FleetLexer. (ANTLRStringStream. template-str))
        parser (FleetParser. (CommonTokenStream. lexer))
        tree (.. parser input getTree)]
    (children tree)))

