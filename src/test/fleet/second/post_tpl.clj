(do
  (use 'ru.flamefork.fleet)
  (use 'ru.flamefork.fleet.runtime)
  (fn [escape-fn post]
    (screen escape-fn [
    (raw "<p>") (screen escape-fn (raw (post :body))) (raw "</p>\n<ul>\n  ")
    (screen escape-fn (for [tag (post :tags)]
      (screen escape-fn [
        (raw "\n    <li>") (screen escape-fn (str tag)) (raw "</li>\n  ")])))
    (raw "\n</ul>")])))
