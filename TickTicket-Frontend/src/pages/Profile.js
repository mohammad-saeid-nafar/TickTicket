import React from "react";
import {Button, Container, Stack, TextField} from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import ProfileCard from "../components/ProfileCard";
// import EventCard from "../components/EventCard";

const Home = () => {
    const [user, setUser] = useState();
    const [loading, setLoading] = useState(false);
    const [userId, setUserId] = useState(localStorage.getItem("userId"));
    const [password, setPassword] = useState("");

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

  useEffect(() => {
      console.log("user id from local storage is null? " + localStorage.getItem("userId") === "");
      // loadData();
      setLoading(true);
      axios
          .get("users/" + localStorage.getItem("userId"))
          .then((res) => {
              setUser(res.data);
              setUserId(localStorage.getItem("userId"));
              console.log(res.data);
              console.log(user);
              console.log("user is null? " + user == null);
              console.log("userID: " + user.id);
              console.log(userId);
          });
      setLoading(false);
      // eslint-disable-next-line
  }, []);

    // const loadData = async () => {
    //     setLoading(true);
    //     await axios
    //         .get("users/" + localStorage.getItem("userId"))
    //         .then((res) => {
    //             setUser(res.data);
    //             setUserId(localStorage.getItem("userId"));
    //             // console.log(res.data);
    //             console.log(user);
    //             console.log("user is null? " + user == null);
    //             console.log("userID: " + user.id);
    //             console.log(userId);
    //         });
    //     setLoading(false);
    // }

    const handleDelete = React.useCallback(() => {
        console.log("Delete button was pressed");
        axios.delete("users/" + userId, {
            params: {
                password: password
             }
    })

    }, [userId, password])

  return (
      <Container
          sx={{
            paddingTop: "5%",
          }}
      >
          <Stack spacing={2}>
              <h1>Profile</h1>
              {!loading && user != null && (
                  <ProfileCard key={user.id} event={user}/>)
              }
          </Stack>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', marginTop: 20}}>
              <TextField  type="password" label="password" variant="standard" onChange={handlePasswordChange}/>
          </div>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', marginTop: 20}}>
              <Button
                  onClick={handleDelete}
                  color="primary"
              >
                  DELETE ACCOUNT
              </Button>
          </div>

      </Container>
  );
};
export default Home;
