(ns fleet-api-test
  (:refer-clojure :exclude [replace])
  (:use
    clojure.test
    fleet))

(def test-posts [{:body "First post."}
                 {:body "Second post."}])

(def test-data {:title "Posts"})

(def filters [
  "js" :str
  "html" :xml
  :fleet :bypass])

(fleet-ns tpl "test/resources/ns" filters)

(deftest fleet-ns-test
  (let [initial-ns *ns*]
    (fleet-ns 'tpl "test/resources/ns" filters)
    (is (= *ns* initial-ns))
    (is (=
      (tpl.posts/posts-html test-posts test-data)
      (slurp "test/resources/ns/posts/posts.html")))))

(deftest error-reporting-test
  (let [^Throwable e (is (thrown? ClassCastException (tpl.posts/exceptional)))
        ^StackTraceElement ste (first (.getStackTrace e))]
    (is (= (.getFileName ste) "exceptional.fleet"))
    (is (= (.getLineNumber ste) 4))))

(deftest cross-lang-test
  (is (=
    (tpl.posts/update (first test-posts))
    (slurp "test/resources/ns/posts/update.js"))))

(deftest layout-test
  (is (=
    (tpl/layout (tpl.posts/posts-html test-posts test-data))
    (slurp "test/resources/ns/posts/posts_with_layout.html"))))
