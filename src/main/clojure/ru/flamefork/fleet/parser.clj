(ns ru.flamefork.fleet.parser
  (:import
    [org.antlr.runtime Token ANTLRStringStream CommonTokenStream]
    [ru.flamefork.fleet FleetLexer FleetParser])
  (:use [clojure.contrib.def]))

(defmacro- jmap [method coll]
  `(map (fn [object#] (~method object#)) ~coll))

(defn- with-children
  [token]
  (cons token (.getChildren token)))

(defn- consume)

(defn- children
  "Converts ANTLR Tree to Fleet AST"
  [token]
  (map consume (.getChildren token)))

(defvar- consumers {
  FleetParser/CHAR           [:text  #(apply str (jmap .getText (with-children %)))]
  FleetParser/SPACESHIP_OPEN [:embed #(children %)]
  FleetParser/SLIPWAY_OPEN   [:tpl   #(children %)]})

(defn- consume
  "Convert functions for various node types"
  [token]
  (let [c (consumers (.getType token))]
    [(first c) ((second c) token)]))

(defn parse
  "Builds Fleet AST from String"
  [template-str]
  (let [lexer (FleetLexer. (ANTLRStringStream. template-str))
        parser (FleetParser. (CommonTokenStream. lexer))
        tree (.. parser input getTree)]
    [:tpl (children tree)]))

