import React from "react";
import { Container, Stack } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import ProfileCard from "../components/ProfileCard";

const Home = () => {
  const [user, setUser] = useState([]);

  useEffect(() => {
    axios
        .get("users/" + localStorage.getItem("userId"))
        .then((res) => {
          setUser(res.data);
          console.log(res.data)
        });
  }, []);

  return (
      <Container
          sx={{
            paddingTop: "5%",
          }}
      >
          <Stack spacing={2}>
            {/*{user.map((event) => {*/}
            {/*    console.log("idddd " + user.username);*/}
              return <ProfileCard key={user.id} event={user} />;
            {/*})}*/}
          </Stack>
      </Container>
  );
};
export default Home;
