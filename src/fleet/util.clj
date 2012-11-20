(ns fleet.util)

(defn bypass
  "Just returns it's argument"
  [arg]
  arg)

;; copied from v1.1 clojure.contrib.lazy-xml
(def ^:private
     escape-xml-map
     (zipmap "'<>\"&" (map #(str \& % \;) '[apos lt gt quot amp])))
(defn escape-xml [text]
  (apply str (map #(escape-xml-map % %) (.toString text))))
  
(defn escape-clj-string
  "Escapes Clojure string."
  [s]
  (.. s
    (replace "\\" "\\\\")
    (replace "\"" "\\\"")))

(defn escape-string
  "Escapes common string causes, like for Java or JS."
  [s]
  (.. s
    (replace "\\" "\\\\")
    (replace "\"" "\\\"")
    (replace "\n" "\\n")))

(defn escape-regex
  "Escapes special Regex symbols."
  [s]
  (.. s
    (replace "\\" "\\\\")
    (replace "("  "\\(")
    (replace ")"  "\\)")
    (replace "."  "\\.")
    (replace "["  "\\[")
    (replace "]"  "\\]")
    (replace "^"  "\\^")
    (replace "$"  "\\$")
    (replace "|"  "\\|")
    (replace "?"  "\\?")
    (replace "*"  "\\*")
    (replace "+"  "\\+")))

; copied from clojure.contrib.with-ns;
; removed eval to support lexical bindings
(defmacro within-ns
  "Evaluates body in another namespace.  ns is either a namespace
  object or a symbol.  This makes it possible to define functions in
  namespaces other than the current one."
  [ns & body]
  `(binding [*ns* (the-ns ~ns)] ~@body))

