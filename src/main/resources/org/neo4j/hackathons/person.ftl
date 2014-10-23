<#-- @ftlvariable title="" type="org.neo4j.hackathons.PersonView" -->
<!doctype html>
<html>

<head>
  <title>${name} - Hackathon Movie Guide</title>
  <link rel="stylesheet" href="/assets/css/main.css">
</head>

<body>

<div class="header">
  <nav><a href="/">Hackathon Movie Guide</a> / <a href="/person/">People</a> / <strong>${name}</strong></nav>
</div>

<h1>${name}</h1>

<h2>Personal Details</h2>
<dl>
  <dt>Name:</dt>
  <dd>${name}</dd>
  <dt>Born:</dt>
  <dd>${born?c}</dd>
</dl>

<h2>Movies</h2>
<ul>
  <#list moviesActedIn as movie>
    <li><a href="${movie.title}">${movie.title}</a> (Actor)</li>
  </#list>

  <#list moviesDirected as movie>
    <li><a href="${movie.title}">${movie.title}</a> (Director)</li>
  </#list>

</ul>

<div class="footer">
  <code>(graphs)-[:ARE]->(everywhere)</code>
  <p>With &hearts; from Sweden &amp; the <a href="http://neo4j.com/community/">Neo4j Community</a></p>
</div>

</body>

</html>
