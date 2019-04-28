window.onload = function() 
{
    loadLogin();
}


async function login() 
{
    let credentials = [];
    credentials.push(document.getElementById('username-cred').value);
    credentials.push(document.getElementById('password-cred').value);

    let response = await fetch('auth', 
    {
        method: 'POST',
        mode: 'cors',
        headers: 
        {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
    });


    if(response.status == 200) 
    {
        document.getElementById('alert-msg').hidden = true;
        localStorage.setItem('jwt', response.headers.get('Authorization'));
        getDashboard();
    } 
    else 
    {
        document.getElementById('alert-msg').hidden = false;
    }
}
    
let results = await response.json();


if (results.constructor === Array) 
{
    loadManager();
} 
else 
{
    loadEmployee();
}


//-----------------------------------------------------------------------------------------

/*
Register component

    - loadRegister()
    - configureRegister()
    ...
    ...
    ...
    - register()
*/

async function loadRegister() 
{
    APP_VIEW.innerHTML = await fetchView('register.view');
    DYNAMIC_CSS_LINK.href = 'css/register.css';
    configureRegister();
}

function configureRegister() 
{
    document.getElementById('alert-msg-username').hidden = true;
    document.getElementById('alert-msg-registration').hidden = true;
    document.getElementById('registration-success').hidden = true;
    document.getElementById('register-username').addEventListener('blur', validateUsername);
    document.getElementById('register-password').addEventListener('blur', validatePassword);
    document.getElementById('register-first-name').addEventListener('blur', validateFirstName);
    document.getElementById('register-last-name').addEventListener('blur', validateLastName);
    document.getElementById('register-email').addEventListener('blur', validateEmail);
    document.getElementById('register-email').addEventListener('input', undisableRegisterButton);
    document.getElementById('register-account').addEventListener('click', register);
    document.getElementById('back-to-login').addEventListener('click', loadLogin);

    document.getElementById('register-password').disabled = true;
    document.getElementById('register-first-name').disabled = true;
    document.getElementById('register-last-name').disabled = true;
    document.getElementById('register-email').disabled = true;
    document.getElementById('register-account').disabled = true;
}

function validateUsername(event) 
{
    if(event.target.value.length < 4 )
    {
        if(document.getElementById('register-username').value == '')
        {  
            document.getElementById('register-password').disabled = true;
        }   
        if(document.getElementById('register-password').value == '')
        {
            document.getElementById('register-first-name').disabled = true;
        }
        if(document.getElementById('register-first-name').value == '')
        {
            document.getElementById('register-last-name').disabled = true;
        }
        if(document.getElementById('register-last-name').value == '')
        {
            document.getElementById('register-email').disabled = true;
        }

        document.getElementById('register-account').disabled = true;
    }

    else
    {
        document.getElementById('register-password').disabled = false;
    }

}

function validatePassword(event) 
{
    if(event.target.value.length < 4 )
    {
        if(document.getElementById('register-password').value == '')
        {
            document.getElementById('register-first-name').disabled = true;
        }
        if(document.getElementById('register-first-name').value == '')
        {
            document.getElementById('register-last-name').disabled = true;
        }
        if(document.getElementById('register-last-name').value == '')
        {
            document.getElementById('register-email').disabled = true;
        }

        document.getElementById('register-account').disabled = true;
    }
    else
    {
        document.getElementById('register-first-name').disabled = false;
    }
}

function validateFirstName(event) 
{
    if(!(/^[a-zA-Z ]+$/.test(event.target.value)))
    {
        if(document.getElementById('register-first-name').value == '')
        {
            document.getElementById('register-last-name').disabled = true;
        }

        if(document.getElementById('register-last-name').value == '')
        {
            document.getElementById('register-email').disabled = true;
        }

        document.getElementById('register-account').disabled = true;
    }

    else
    {
        document.getElementById('register-last-name').disabled = false;
    }
}

function validateLastName(event) 
{
    if(!(/^[a-zA-Z ]+$/.test(event.target.value)))
    {
        if(document.getElementById('register-last-name').value == '')
        {
            document.getElementById('register-email').disabled = true;
        }

        document.getElementById('register-account').disabled = true;
    }

    else
    {
        document.getElementById('register-email').disabled = false;
    }
}

function validateEmail(event) 
{
    if(!(/\S+@\S+\.\S+/.test(event.target.value)))
    {
        document.getElementById('register-account').disabled = true;
    }
    else
    {
        document.getElementById('register-account').disabled = false;
    }
}

function undisableRegisterButton() 
{
    if  (
        document.getElementById('register-username').value != '' &&
        document.getElementById('register-password').value != '' &&
        document.getElementById('register-first-name').value != '' &&
        document.getElementById('register-last-name').value != '' &&
        document.getElementById('register-email').value != ''
        ) 
    {
        document.getElementById('register-account').disabled = false;
    } 
    else 
    {
        document.getElementById('register-account').disabled = true;
    }  
}

async function register() 
{
    let newUser = 
    {
        id: 0,
        username: document.getElementById('register-username').value,
        password: document.getElementById('register-password').value,
        firstName: document.getElementById('register-first-name').value,
        lastName: document.getElementById('register-last-name').value,
        email: document.getElementById('register-email').value,
        role: {}
    };

    let response = await fetch('users', 
    {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newUser)
    });

    let responseBody = await response.json();

    if(response.status == 409) 
    {
        document.getElementById('alert-msg-username').hidden = false;
        return;
    }

    if(responseBody != null) 
    {    
        document.getElementById('alert-msg-registration').hidden = true;
        document.getElementById('registration-success').hidden = false;
        setTimeout(loadLogin, 3000);
        
    } 
    else 
    {
        document.getElementById('alert-msg-registration').hidden = false;
    }

}

//-------------------------------------------------------------------------------------

/*
Employee component
*/

async function loadEmployee() 
{
    APP_VIEW.innerHTML = await fetchView('employee.view');
    DYNAMIC_CSS_LINK.href = 'css/employee.css';
    getCurrentUserReimRequests();
    configureEmployee();
}

function configureEmployee() 
{
    document.getElementById('new-reim-request').addEventListener('click',loadReimbursement);
    document.getElementById('logout').addEventListener('click', loadLogin);
    document.getElementById('logout-again').addEventListener('click', loadLogin);
}

async function getCurrentUserReimRequests() 
{
    let response = await fetch('reimbursements',
    {
        method: 'GET',
        mode: 'cors',
        headers: 
        {
            'Authorization' : localStorage.getItem('jwt')
        }
        
    });
    
    let results = await response.json();
    
    createResultsContainer(results);
}


function createResultsContainer(results) 
{
    for(let i=0; i < results.length; i++) 
    {
        let row = document.createElement('tr');
        let reimbIdCell = document.createElement('td');
        let amountCell = document.createElement('td');
        let submittedCell = document.createElement('td');
        let resolvedCell = document.createElement('td');
        let descriptionCell = document.createElement('td');
        let statusCell = document.createElement('td');
        let typeCell = document.createElement('td');

        row.appendChild(reimbIdCell);
        row.appendChild(amountCell);
        row.appendChild(submittedCell);
        row.appendChild(resolvedCell);
        row.appendChild(descriptionCell);
        row.appendChild(statusCell);
        row.appendChild(typeCell);

        document.getElementById('employee-reimbursements').appendChild(row);

        reimbIdCell.innerText = results[i].id;
        amountCell.innerText = results[i].amount;
        submittedCell.innerText = results[i].submitted;
        
        if(results[i].resolved == null) 
        {
            resolvedCell.innerText = 'pending';
        } 
        else 
        {
            resolvedCell.innerText = results[i].resolved;
        }

        descriptionCell.innerText = results[i].description;
        statusCell.innerText = results[i].reimbStatus.reimbStatusName;
        typeCell.innerText = results[i].reimbType.reimbTypeName;
    }
}



/*
Manager component--------------------------
*/

async function loadManager() 
{
    APP_VIEW.innerHTML = await fetchView('managers.view');
    DYNAMIC_CSS_LINK.href = 'css/manager.css';
    getAllReimRequests();
    configureManager();
}

function configureManager() 
{
    document.getElementById('view-all').addEventListener('click', getDashboard);
    document.getElementById('new-reim-request').addEventListener('click',loadReimbursement);
    document.getElementById('logout').addEventListener('click', loadLogin);
    document.getElementById('logout-again').addEventListener('click', loadLogin);
}

async function getAllReimRequests() 
{
    let response = await fetch('reimbursements',
    {
        method: 'GET',
        mode: 'cors',
        headers: 
        {
            'Authorization' : localStorage.getItem('jwt')
        }
        
    });
    let results = await response.json();

    getUsersInfo(results);
}

async function getUsersInfo(results) 
{    
    let response = await fetch('users',
    {
        method: 'GET',
        mode: 'cors',
        headers: 
        {
            'Authorization' : localStorage.getItem('jwt')
        }   
    });
    let resultsTwo = await response.json();

    createResultsContainerTwo(results, resultsTwo);
}   


//------------------------------------------------------------------------------------
//New Reimbursement component

async function loadReimbursement() 
{
    APP_VIEW.innerHTML = await fetchView('reimbursement.view');
    DYNAMIC_CSS_LINK.href = 'css/reimbursement.css';
    configureReimbursement();
}

function configureReimbursement() 
{
    document.getElementById('alert-msg-reimbursement').hidden = true;
    document.getElementById('reimbursement-success').hidden = true;
    document.getElementById('reim-amount').addEventListener('blur', validateAmount);
    document.getElementById('reim-description').addEventListener('blur', validateDescription);
    document.getElementById('reim-description').addEventListener('input', undisableNewReimbursementButton);
    document.getElementById('register-reimbursement').addEventListener('click', newReimbursement);
    document.getElementById('back-to-dashboard').addEventListener('click', getDashboard);
    document.getElementById('reim-description').disabled = true;
    document.getElementById('register-reimbursement').disabled = true;
}

function validateAmount(event) 
{
    if ((/^\s*-?\d+(\.\d{1,2})?\s*$/).test(event.target.value) && 0 < event.target.value.length && event.target.value.length < 8
    && document.getElementById('reim-amount').value >= 1) 
    {
        document.getElementById('reim-description').disabled = false;
    } 
    else 
    {
        if(document.getElementById('reim-amount').value == '') 
        {
            document.getElementById('reim-description').disabled = true;
        }
        
        document.getElementById('register-reimbursement').disabled = true;
    }
}

function validateDescription(event) 
{
    if (10 < event.target.value.length && event.target.value.length < 250) 
    {
        document.getElementById('register-reimbursement').disabled = false;
    } 
    else 
    {
        document.getElementById('register-reimbursement').disabled = true;
    }
}

function undisableNewReimbursementButton () 
{
    if(document.getElementById('reim-amount').value != '' 
    && document.getElementById('reim-description').value != ''
    && document.getElementById('reim-amount').value >= 1) 
    {
        document.getElementById('register-reimbursement').disabled = false;
    } 
    else 
    {
        document.getElementById('register-reimbursement').disabled = true;
    }
}


async function newReimbursement() 
{
    let today = new Date();
    let date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
    let time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
    let dateTime = date+' '+time;

    let reimbTypeElement = document.getElementById('reim-type');
    let reimbursementType = reimbTypeElement.options[reimbTypeElement.selectedIndex].value;
    let reimbursementTypeId = 0;
    switch(reimbursementType)
    {
        case "lodging":
        reimbursementTypeId = 1;
        break;
        case "travel":
        reimbursementTypeId = 2;
        break;
        case "food":
        reimbursementTypeId = 3;
        break;
        case "other":
        reimbursementTypeId = 4;
        break;
    }
    
    let newReim = 
    {
        id: 0,
        amount: document.getElementById('reim-amount').value,
        submitted: dateTime,
        resolved: '',
        description: document.getElementById('reim-description').value,
        receipt: null,
        author: 0,
        resolver: 0,
        reimbStatus:
        {
        reimbStatusId: '1',
        reimbStatusName: 'pending'
        },
        reimbType:
        {
        reimbTypeId: `${reimbursementTypeId}`,
        reimbTypeName: `${reimbursementType}`
        }

    };

    let response = await fetch('reimbursements', 
    {
        method: 'POST',
        mode: 'cors',
        headers: {
        'Authorization' : localStorage.getItem('jwt')
        },
        body: JSON.stringify(newReim)
    });

    let responseBody = await response.json();
    if (responseBody != null) 
    {
        document.getElementById('alert-msg-reimbursement').hidden = true;
        document.getElementById('reimbursement-success').hidden = false;
        setTimeout(getDashboard, 3000);
    } 
    else 
    {
        document.getElementById('alert-msg-reimbursement').hidden = false;
    }
}


//Updating reimbursements
async function approveReimbursementRequest(reimbId, amount, submitted, description, author, type) 
{

    let today = new Date();
    let date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
    let time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
    let dateTime = date+' '+time;

    let reimbursementTypeId = 0;
    switch(type)
    {
        case "lodging":
        reimbursementTypeId = 1;
        break;
        case "travel":
        reimbursementTypeId = 2;
        break;
        case "food":
        reimbursementTypeId = 3;
        break;
        case "other":
        reimbursementTypeId = 4;
        break;
    }

    let updatedReim = 
    {
        id: reimbId,
        amount: amount,
        submitted: submitted,
        resolved: dateTime,
        description: description,
        receipt: null,
        author: author,
        resolver: 0,
        reimbStatus:
        {
            reimbStatusId: '2',
            reimbStatusName: 'approved'
        },
        reimbType:
        {
            reimbTypeId: `${reimbursementTypeId}`,
            reimbTypeName: `${type}`
        }

    };

    let response = await fetch('reimbursements', 
    {
        method: 'POST',
        mode: 'cors',
        headers: 
        {
            'Authorization' : localStorage.getItem('jwt')
        },
        body: JSON.stringify(updatedReim)
    });

    let responseBody = await response.json();
    return responseBody;
}

async function denyReimbursementRequest(reimbId, amount, submitted, description, author, type) 
{

    let today = new Date();
    let date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
    let time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
    let dateTime = date+' '+time;

    let reimbursementTypeId = 0;
    switch(type)
    {
        case "lodging":
        reimbursementTypeId = 1;
        break;
        case "travel":
        reimbursementTypeId = 2;
        break;
        case "food":
        reimbursementTypeId = 3;
        break;
        case "other":
        reimbursementTypeId = 4;
        break;
    }

    let updatedReim = 
    {
        id: reimbId,
        amount: amount,
        submitted: submitted,
        resolved: dateTime,
        description: description,
        receipt: null,
        author: author,
        resolver: 0,
        reimbStatus:
        {
            reimbStatusId: '3',
            reimbStatusName: 'denied'
        },
        reimbType:
        {
            reimbTypeId: `${reimbursementTypeId}`,
            reimbTypeName: `${type}`
        }

    };

    let response = await fetch('reimbursements', 
    {
        method: 'POST',
        mode: 'cors',
        headers: 
        {
            'Authorization' : localStorage.getItem('jwt')
        },

        body: JSON.stringify(updatedReim)
    });

    let responseBody = await response.json();
    return responseBody;
}




