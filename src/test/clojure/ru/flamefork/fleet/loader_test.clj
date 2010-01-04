(ns ru.flamefork.fleet.loader-test
  (:use
    [clojure.test]
    [ru.flamefork.fleet.loader]))

(swap! *fleet-template-paths* conj "src/test/fleet/first")
(swap! *fleet-template-paths* conj "src/test/fleet/second")
(swap! *fleet-template-paths* conj "src/test/fleet/third")

(deftest make-file-test
  (is (=
    (.getPath (@#'ru.flamefork.fleet.loader/make-file "/tpls" "post-list"))
    "/tpls/post_list.fleet")))

(deftest find-file-test
  (is (=
    (.getPath (find-file "post"))
    (.getCanonicalPath (java.io.File. "." "src/test/fleet/second/post.fleet")))))


