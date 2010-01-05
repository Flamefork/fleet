(ns ru.flamefork.fleet.loader-test
  (:use
    [clojure.test]
    [ru.flamefork.fleet.loader]))

(def paths
  ["src/test/fleet/first"
   "src/test/fleet/second"
   "src/test/fleet/third"])

(deftest find-file-test
  (is (=
    (.getPath (find-file "post" paths))
    (.getCanonicalPath (java.io.File. "." "src/test/fleet/second/post.fleet")))))

(deftest failed-find-file-test
  (is (thrown?
    java.io.FileNotFoundException
    (find-file "not-existing" paths))))
