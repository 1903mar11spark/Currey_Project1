window.onload = function() 
{
    loadLogin();
}

async function login() 
{
    let credentials = [];
    credentials.push(document.getElementById('username').value);
    credentials.push(document.getElementById('password').value);

    let response = await fetch('auth', 
    {
        method: 'POST',
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

async function loadRegister() 
{
    APP_VIEW.innerHTML = await fetchView('register.view');
    DYNAMIC_CSS_LINK.href = 'css/register.css';
    configureRegister();
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


//-----------------------------------------------------------------------------------------


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

async function loadManager() 
{
    APP_VIEW.innerHTML = await fetchView('managers.view');
    DYNAMIC_CSS_LINK.href = 'css/manager.css';
    getAllReimRequests();
    configureManager();
}  


//------------------------------------------------------------------------------------s



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