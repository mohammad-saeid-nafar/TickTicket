import React from "react";
import {Button, Container, Stack} from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import ProfileCard from "../components/ProfileCard";

const Home = () => {
  const [user, setUser] = useState(null);

  useEffect(() => {
      // console.log("user id is: " +localStorage.getItem("userId"));
    axios
        .get("users/" + localStorage.getItem("userId"))
        .then((res) => {
          setUser(res.data);
          console.log(res.data);
          console.log(user.id);
        });
  }, []);

  return (
      <Container
          sx={{
            paddingTop: "5%",
          }}
      >
          <Stack spacing={2}>
              <h1> hi</h1>

            <ProfileCard key={user.id} event={user} />
              <Button
                  onClick={() => {
                      this.setState({ editing: true });
                      setUser(user);
                      console.log("hello");
                  }}
                  color="primary"
              >
                  DELETE ACCOUNT
              </Button>
          </Stack>
      </Container>
  );
};
export default Home;
