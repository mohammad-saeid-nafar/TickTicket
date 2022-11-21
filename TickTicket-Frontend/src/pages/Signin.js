import { Button, TextField, Alert } from "@mui/material";
import axios from "axios";
import * as React from 'react';
import Stack from '@mui/material/Stack';


const Signin = () => {

  const [username, setUsername] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [error, setError] = React.useState(false);
  const [errorMessage, setErrorMessage] = React.useState("");
  const [success, setSuccess] = React.useState(false);
  const [disable, setDisable] = React.useState(true);

  React.useEffect(() => {
    if (username && password){
      setDisable(false);
    }
    else{
      setDisable(true);
    }
  }, [username, password]);

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleLogin = React.useCallback(() => {
   if(!isNaN(username)){
      setError(true);
      setErrorMessage("Username Must Be A String");
    }
    else if(!isNaN(password)){
      setError(true);
      setErrorMessage("Password Must Be A String");
    }
    else{
      axios.post("users/login", null, { params:
        {
          username,
          password
        }
      })
      .then(res => {
        localStorage.setItem("userId", res.data.id);
        localStorage.setItem("username", res.data.username);
        window.location.href = "/";
        setSuccess(true);
      })
      .catch(function (error) {
        setError(true);
        setErrorMessage("One input was wrongly entered, please check your choices again");
      });
          
    }
}, [username, password]);

  return (

  <>
  <div>
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', marginTop: 120}}>
      <h1>Sign in</h1>
    </div>
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', marginTop: 20}}>
      <TextField label="username" variant="standard" onChange={handleUsernameChange}/>
    </div>
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', marginTop: 20}}>
      <TextField type="password" label="password" variant="standard" onChange={handlePasswordChange}/>
    </div>
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', marginTop: 20}}>
      <Button variant='contained' onClick={handleLogin} color="primary" disabled={disable}>
        Log in
      </Button>
    </div>
  </div>
  <Stack sx={{ width: '100%' }} spacing={2}>
      {error && <Alert severity="error">{errorMessage}</Alert>}
      {success && <Alert severity="success">Signed in</Alert>}
  </Stack>
  </>
  )
};
export default Signin;
