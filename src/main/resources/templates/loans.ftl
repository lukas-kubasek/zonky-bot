<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="refresh" content="30">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <title>Zonky Loan Bot</title>
</head>
<body>

<div class="container-fluid">

    <nav class="navbar navbar-dark bg-dark mb-3">
        <span class="navbar-brand mb-0 h1">Zonky Bot</span>
        <span class="navbar-brand mb-0">Latest Loans</span>
    </nav>

    <div class="alert alert-secondary" role="alert">
        Auto-refresh has been enabled. Newly published loans will appear at the top.
    </div>

    <#list loans as loan>
        <div class="card mb-3">
            <div class="card-body">
                <h5 class="card-title">${loan.name}</h5>
                <h6 class="card-subtitle mb-4 text-muted"><a href="${loan.url!}" target="_blank">${loan.url!}</a></h6>
                <table class="table table-hover table-sm smaller">
                    <tbody>
                    <tr>
                        <th>ID</th>
                        <td>${loan.id?c}</td>
                    </tr>
                    <tr>
                        <th>Date Published</th>
                        <td>${loan.datePublished!}</td>
                    </tr>
                    <tr>
                        <th>Deadline</th>
                        <td>${loan.deadline!}</td>
                    </tr>
                    <tr>
                        <th>Term Months</th>
                        <td>${loan.termInMonths!}</td>
                    </tr>
                    <tr>
                        <th>Interest Rate</th>
                        <td>${loan.interestRate!}</td>
                    </tr>
                    <tr>
                        <th>Rating</th>
                        <td>${loan.rating!}</td>
                    </tr>
                    <tr>
                        <th>Amount</th>
                        <td>${loan.amount!}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </#list>

</div>

</body>
</html>