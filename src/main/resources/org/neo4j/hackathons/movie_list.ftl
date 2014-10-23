<#-- @ftlvariable title="" type="org.neo4j.hackathons.MovieListView" -->
<!doctype html>
<html>

<head>
  <title>Person List - Hackathon Movie Guide</title>
  <link rel="stylesheet" href="/assets/css/main.css">
</head>

<body>

<div class="header">
  <nav><a href="/">Hackathon Movie Guide</a> / <strong>Movies</strong></nav>
</div>

<h1>People</h1>
<ul>
    <#list movies as movie>
      <li><a href="${movie.title}">${movie.title}</a></li>
    </#list>

</ul>

<div class="footer">
  <code>(graphs)-[:ARE]->(everywhere)</code>
  <p>With &hearts; from Sweden &amp; the <a href="http://neo4j.com/community/">Neo4j Community</a></p>
</div>

</body>

</html>

