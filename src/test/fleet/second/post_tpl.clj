(do (use 'fleet.runtime)(fn [escape-fn post] (screen escape-fn [(raw "<p>")(screen escape-fn (raw (post :body)))(raw "</p>
<ul>
  ")(screen escape-fn (for [tag (post :tags)] (screen escape-fn [(raw "
    <li>")(screen escape-fn (str tag))(raw "</li>
  ")])))(raw "
</ul>")])))