(ns ru.flamefork.fleet
  (:import
    [org.antlr.runtime Token ANTLRStringStream CommonTokenStream]
    [ru.flamefork.fleet.antlr FleetLexer FleetParser])
  (:use [clojure.contrib.seq-utils :only (partition-by)]))

(defmulti consume
  "Convert functions for various node types"
  #(.getType %))

(defn- children
  "Converts ANTLR Tree to Fleet AST"
  [tree]
  (map consume (.getChildren tree)))

(defmethod consume FleetParser/CHARS
  [token]
  token)

(defmethod consume FleetParser/SPACESHIP_OPEN
  [token]
  [:embed (children token)])

(defmethod consume FleetParser/SLIPWAY_OPEN
  [token]
  [:tpl (children token)])

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