import React from "react";
import {Button, Container, Stack} from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
// import ProfileCard from "../components/ProfileCard";

const Home = () => {
  const [user, setUser] = useState(null);
  const [userId, setUserId] = useState(null);

  useEffect(() => {
      console.log("user id is: " +localStorage.getItem("userId"));
      loadData();
      // eslint-disable-next-line
  }, []);

    const loadData = async () => {
        axios
            .get("users/" + localStorage.getItem("userId"))
            .then((res) => {
                setUser(res.data);
                setUserId(localStorage.getItem("userId"));
                console.log(res.data);
                console.log(user.id);
            });
    }

    const handleDelete = React.useCallback(() => {

        axios.delete("users/", {
            userId
        })

    }, [userId])

  return (
      <Container
          sx={{
            paddingTop: "5%",
          }}
      >
          <Stack spacing={2}>
              <h1> hi</h1>

            {/*<ProfileCard key={user.id} event={user} />*/}
              <Button
                  onClick={handleDelete}
                  color="primary"
              >
                  DELETE ACCOUNT
              </Button>
          </Stack>
      </Container>
  );
};
export default Home;
