(ns fleet.loader
  (:import
    java.io.File)
  (:require
    [clojure.string :as cs]))

(defn- ns-from-path
  [^String path]
  (symbol (.. path
    (replace \_ \-)
    (replace \. \-)
    (replace File/separatorChar \.))))

(defn- names-from-filename
  "Make (post-html-fleet post-html post) from 'post.html.fleet'"
  [filename]
  (map #(ns-from-path (cs/join \. %))
    (loop [s (cs/split filename #"\.")
           names []]
      (if (nil? s)
        names
        (recur (butlast s) (conj names s))))))

(defn- path-diff
  [^String prefix ^String s]
  {:pre [(.startsWith s prefix)]}
  (.substring s (+ (.length prefix) 1)))

(defn- relative-path
  "File path relative to root. Only if file inside root."
  [^File root ^File file]
  (let [file-path (.getCanonicalPath file)
        root-path (.getCanonicalPath root)]
    (if (= file-path root-path)
      ""
      (path-diff root-path file-path))))

(defn- make-tpl-info
  [^File root ^File file]
  (let [ns (ns-from-path (relative-path root (.getParentFile file)))]
    {:content (slurp (.getPath file))
     :names (names-from-filename (.getName file))
     :file-path (.getAbsolutePath file)
     :file-name (.getName file)
     :ns ns}))

(defn make-tpl-infos
  [^String root-path file-filter]
  (let [root (File. root-path)
        nodes (file-seq root)
        files (filter file-filter nodes)]
    (map (partial make-tpl-info root) files)))

(defn load-fleet-string
  [s file-path file-name]
  (-> s
    (java.io.StringReader.)
    (clojure.lang.LineNumberingPushbackReader.)
    (clojure.lang.Compiler/load file-path file-name)))
