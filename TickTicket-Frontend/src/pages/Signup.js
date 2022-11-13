import { Button, Alert, TextField } from "@mui/material";
import axios from "axios";
import * as React from 'react';
import Stack from '@mui/material/Stack';


const Signup = () => {

  const [firstName, setFirstName] = React.useState("");
  const [lastName, setLastName] = React.useState("");
  const [email, setEmail] = React.useState("");
  const [address, setAddress] = React.useState("");
  const [phoneNumber, setPhoneNumber] = React.useState("");
  const [dob, setDob] = React.useState("");
  const [username, setUsername] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [confirmPassword, setConfirmPassword] = React.useState("");
  const [success, setSuccess] = React.useState(false);
  const [error, setError] = React.useState(false);
  const [errorMessage, setErrorMessage] = React.useState("");
  const [disable, setDisable] = React.useState(true);

  React.useEffect(() => {
    if (firstName && lastName && email && address && phoneNumber && dob && username && password && confirmPassword){
      setDisable(false);
    }
    else{
      setDisable(true);
    }
  }, [firstName, lastName, email, phoneNumber, address, dob, username, password, confirmPassword]);

  const handleFirstNameChange = (event) => {
    setError(false);
    setFirstName(event.target.value);
  };

  const handleLastNameChange = (event) => {
    setError(false);
    setLastName(event.target.value);
  };

  const handleEmailChange = (event) => {
    setError(false);
    setEmail(event.target.value);
  };

  const handleAddressChange = (event) => {
    setError(false);
    setAddress(event.target.value);
  };

  const handlePhoneNumberChange = (event) => {
    setError(false);
    setPhoneNumber(event.target.value);
  };

  const handleDobChange = (event) => {
    setError(false);
    setDob(event.target.value);
  };

  const handleUsernameChange = (event) => {
    setError(false);
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setError(false);
    setPassword(event.target.value);
  };

  const handleConfirmPasswordChange = (event) => {
    setError(false);
    setConfirmPassword(event.target.value);
  };

  // function sayHello() {
  //   alert(firstName + lastName + email + address + phoneNumber + dob + username + password + confirmPassword + error);
  // }

  const handleSignUp = React.useCallback(() => {
    if(!isNaN(firstName)){
        setError(true);
        setErrorMessage("First Name Must Be A String");
    }
    else if(!isNaN(lastName)){
        setError(true);
        setErrorMessage("Last Name Must Be A String");
    }
    else if(!isNaN(email)){
      setError(true);
      setErrorMessage("Email Must Be A String");
    }
    else if(!isNaN(address)){
      setError(true);
      setErrorMessage("Address Description Must Be A String");
    }
    else if(isNaN(phoneNumber)){
      setError(true);
      setErrorMessage("Phone Number Description Must Be A Number");
    }
    else if(!isNaN(dob)){
      setError(true);
      setErrorMessage("Date Of Birth Must Be A String");
    }    
    else if(!isNaN(username)){
      setError(true);
      setErrorMessage("Username Must Be A String");
    }    
    else if(!isNaN(password) || !isNaN(confirmPassword)){
      setError(true);
      setErrorMessage("Password Must Be A String");
    } else if (password !== confirmPassword) {
      setError(true);
      setErrorMessage("Passwords Don't Match");
    }
    else{
        axios.post("users", {
            username: username,
            password: password,
            // confirmPassword: confirmPassword
            profile: {
              firstName: firstName,
              lastName: lastName,
              email: email,
              address: address,
              phoneNumber: parseInt(phoneNumber),
              dateOfBirth: dob,
            }
          })
          .then(function (response) {
            setSuccess(true);
          })
          .catch(function (error) {
            setError(true);
            setErrorMessage("One input was wrongly entered, please check your choices again");
          });
    }
}, [firstName, lastName, email, phoneNumber, address, dob, username, password, confirmPassword]);

  return (
  <>
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', marginTop: 120}}>
      <h1>Sign up</h1>
    </div>
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', marginTop: 20}}>
      <TextField label="First name" variant="standard" required="true" onChange={handleFirstNameChange}/>
      <TextField label="Last name" variant="standard" required="true" onChange={handleLastNameChange}/>
      <TextField label="Email" variant="standard"  required="true" onChange={handleEmailChange}/>
    </div>
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', marginTop: 20}}>
      <TextField label="Address" variant="standard" onChange={handleAddressChange}/>
      <TextField label="Phone number" variant="standard" required="true" onChange={handlePhoneNumberChange}/>
      <TextField label="Date of birth" variant="standard" required="true" onChange={handleDobChange}/>
    </div>
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', marginTop: 20}}>
      <TextField label="username" variant="standard" required="true" onChange={handleUsernameChange}/>
      <TextField type="password" label="password" variant="standard" required="true" onChange={handlePasswordChange}/>
      <TextField type="password" label="password" variant="standard" required="true" onChange={handleConfirmPasswordChange}/>
    </div>
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', marginTop: 20}}>
      <Button variant='contained' onClick={handleSignUp} color="primary" disabled={disable}>
        Sign Up
      </Button>
    </div>
    <Stack sx={{ width: '100%' }} spacing={2}>
      {error && <Alert severity="error">{errorMessage}</Alert>}
      {success && <Alert severity="success">User account has been successfully created!</Alert>}
    </Stack>
  </>
  )
};
export default Signup;
