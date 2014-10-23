<!doctype html>
<html>

<head>
  <title>Person List - Hackathon Movie Guide</title>
  <link rel="stylesheet" href="/assets/css/main.css">
</head>

<body>

<div class="header">
  <nav><a href="/">Hackathon Movie Guide</a> / <strong>People</strong></nav>
</div>

<h1>People</h1>
<ul>
  % for name, in people:
  <li><a href="/person/{{name}}">{{name}}</a></li>
  % end
</ul>

<div class="footer">
  <code>(graphs)-[:ARE]->(everywhere)</code>
  <p>With &hearts; from Sweden &amp; the <a href="http://neo4j.com/community/">Neo4j Community</a></p>
</div>

</body>

</html>

