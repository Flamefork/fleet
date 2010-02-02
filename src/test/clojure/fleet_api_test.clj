(ns fleet-api-test
  (:use
    [clojure.test]
    [fleet]))

(def test-posts [{:body "First post."}
                 {:body "Second post."}])

(def test-data {:title "Posts"})

(deftest fleet-ns-test
  (let [initial-ns *ns*]
    (fleet-ns "src/test/fleet/ns")
    (is (= *ns* initial-ns))
    (is (=
      ((resolve 'posts/posts-html) test-posts test-data)
      (slurp "src/test/fleet/ns/posts/posts.html")))))

(deftest error-reporting-test
  (fleet-ns "src/test/fleet/ns")

  (let [e (is (thrown? ClassCastException ((resolve 'posts/exceptional))))
        ste (first (.getStackTrace e))]
    (is (= (.getFileName ste) "exceptional.fleet"))
    (is (= (.getLineNumber ste) 4))))

(deftest cross-lang-test
  (fleet-ns "src/test/fleet/ns", ["js" :str, "html" :xml])
  (is (=
    ((resolve 'posts/update) (first test-posts))
    (slurp "src/test/fleet/ns/posts/update.js"))))
