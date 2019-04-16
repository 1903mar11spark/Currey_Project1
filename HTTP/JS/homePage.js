window.onload = function() 
    {
        let submitButton = null; 
        let endorsementButton = null;


        if (document.getElementById('submit-btn'))
        { 
            document.getElementById('submit-btn').addEventListener('click',submit);
        }
    }