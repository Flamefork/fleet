(ns ru.flamefork.fleet.loader-test
  (:use
    [clojure.test]
    [ru.flamefork.fleet.loader]))

(swap! *fleet-template-paths* conj "src/test/fleet/first")
(swap! *fleet-template-paths* conj "src/test/fleet/second")
(swap! *fleet-template-paths* conj "src/test/fleet/third")

(deftest find-file-test
  (is (=
    (.getPath (find-file "post"))
    (.getCanonicalPath (java.io.File. "." "src/test/fleet/second/post.fleet")))))

(deftest failed-find-file-test
  (is (thrown?
    java.io.FileNotFoundException
    (find-file "not-existing"))))
