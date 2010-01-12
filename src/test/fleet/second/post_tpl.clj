(do
  (use 'ru.flamefork.fleet.runtime)
  (fn [post]
    (let [f @ru.flamefork.fleet/escape-fn]
      (screen f [
      (raw "<p>") (screen f (raw (post :body))) (raw "</p>\n<ul>\n  ")
      (screen f (for [tag (post :tags)]
        (screen f [
          (raw "\n    <li>") (screen f (str tag)) (raw "</li>\n  ")])))
      (raw "\n</ul>")]))))
