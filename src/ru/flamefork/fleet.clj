(ns ru.flamefork.fleet
  (:import
    [org.antlr.runtime Token ANTLRStringStream CommonTokenStream]
    [ru.flamefork.fleet.antlr FleetLexer FleetParser])
  (:use [clojure.contrib.seq-utils :only (partition-by)]))

(defmulti transform-group
  "Convert functions for various node types"
  #(.getType (first %)))

(defn- children
  "Converts ANTLR Tree to Fleet AST"
  [tree]
  (map transform-group
    (partition-by
      #(.getType %)
      (.getChildren tree))))

(defmethod transform-group FleetParser/CHAR
  [group]
  (apply str group))

(defmethod transform-group FleetParser/SPACESHIP_OPEN
  [group]
  [:spaceship (children (first group))])

(defmethod transform-group FleetParser/SLIPWAY_OPEN
  [group]
  [:slipway (children (first group))])

(defn- tokenize
  "Builds ANTLR Tree from String"
  [template-str]
  (let [lexer (FleetLexer. (ANTLRStringStream. template-str))
        parser (FleetParser. (CommonTokenStream. lexer))]
    (.. parser input getTree)))

(defn- process
  "Compile template from AST"
  [ast]
  ; TODO
  ast)

(defn fleet
  "Creates anonymous function from template containing in template-str"
  [template-str]
  (-> template-str tokenize children process))

(println (fleet "
<p><(post :body)></p>
<ul>
  <(for [tag (post :tags] \">
    <li><(tag)></li>
  <\")>
</ul>
"))