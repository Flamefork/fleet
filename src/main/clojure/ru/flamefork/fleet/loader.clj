(ns ru.flamefork.fleet.loader
  (:import
    [java.io File]))

(defn find-file
  "Tries to find file with 'name' in 'paths'. Returns first occurence."
  [name paths]
  (let [filename (str (.replace name \- \_) ".fleet")
        options (map #(File. % filename) paths)
        file (first (filter #(.exists %) options))]
    (if file
      (.getCanonicalFile file)
      (throw (java.io.FileNotFoundException. (str
        "Fleet template file '" filename "' not found.\nSearch paths:"
        (if (empty? paths)
          " <none>"
          (map #(str "\n" %) paths))))))))
