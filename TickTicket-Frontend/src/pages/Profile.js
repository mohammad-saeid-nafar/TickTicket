import React from "react";
import {Alert, Button, Container, Stack, TextField} from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import ProfileCard from "../components/ProfileCard";

const Home = () => {
    const [user, setUser] = useState();
    const [loading, setLoading] = useState(false);
    const [userId, setUserId] = useState(localStorage.getItem("userId"));
    const [password, setPassword] = useState("");
    const [success, setSuccess] = useState(false);
    const [successMessage, setSuccessMessage] = useState("");
    const [error, setError] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const [visible, setVisible] = useState(false);

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

  useEffect(() => {
      console.log("user id from local storage is null? " + localStorage.getItem("userId") === "");
      setLoading(true);
      loadData();
      setLoading(false);
      // eslint-disable-next-line
  }, []);

    const loadData = () => {
        setLoading(true);
        axios
            .get("users/" + localStorage.getItem("userId"))
            .then((res) => {
                setUser(res.data);
                setUserId(localStorage.getItem("userId"));
                console.log(userId);
            });
        setLoading(false);
    }

    const handleDelete = React.useCallback(() => {

        setVisible(true);
        console.log("Delete button was pressed");
        if(visible) {
            axios.delete("users/" + userId, {
                params: {
                    password: password
                }
            }).then(res => {
                setSuccess(true);
                setSuccessMessage("Account was deleted successfully");
                localStorage.removeItem("userId");
                window.location.href = "/";
            }).catch(function (error) {
                setError(true);
                setErrorMessage("Account could not be deleted.");
            });
        }
        console.log(errorMessage)
    }, [userId, password, visible, errorMessage])

  return (
      <Container
          sx={{
            paddingTop: "5%",
          }}
      >
          <Stack spacing={2}>
              <h1>Profile</h1>
              {!loading && user != null && (
                  <ProfileCard key={user.id} user={user}/>)
              }
          </Stack>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', marginTop: 20}}>
              { visible && <TextField type="password" label="password" variant="standard" onChange={handlePasswordChange}/>}
          </div>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', marginTop: 20}}>
              <Button onClick={handleDelete} color="primary">
                  DELETE ACCOUNT
              </Button>
          </div>
          <Stack sx={{ width: '100%' }} spacing={2}>
              {error && <Alert severity="error">{errorMessage}</Alert>}
              {success && <Alert severity="success">{successMessage}</Alert>}
          </Stack>
      </Container>
  );
};
export default Home;
