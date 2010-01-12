(ns ru.flamefork.fleet.parser
  (:import
    [org.antlr.runtime Token ANTLRStringStream CommonTokenStream]
    [ru.flamefork.fleet FleetLexer FleetParser])
  (:use [clojure.contrib.def]))

(defn- with-children
  [token]
  (cons token (.getChildren token)))

(defn- consume)

(defn- children
  "Converts ANTLR Tree to Fleet AST"
  [mode token]
  (map (partial consume mode) (.getChildren token)))

(defn- text-consumer
  [token]
  (apply str (map (memfn getText) (with-children token))))

(defvar- consumers {
  :embed {
    FleetParser/SLIPWAY_OPEN   [:tpl (partial children :tpl)]
    FleetParser/CHAR           [:clj text-consumer]
    }
  :tpl {
    FleetParser/SPACESHIP_OPEN [:embed (partial children :embed)]
    FleetParser/CHAR           [:text text-consumer]
    }
  })  

(defn- consume
  "Convert functions for various node types"
  [mode token]
  (let [[type consumer] ((consumers mode) (.getType token))]
    [type (consumer token)]))

(defn parse
  "Builds Fleet AST from String"
  [template-str]
  (let [lexer (FleetLexer. (ANTLRStringStream. template-str))
        parser (FleetParser. (CommonTokenStream. lexer))
        tree (.. parser input getTree)]
    [:tpl (children :tpl tree)]))

