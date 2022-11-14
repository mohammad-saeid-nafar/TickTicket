import React from "react";
import ListItem from "@mui/material/ListItem";
import Rating from "@mui/material/Rating";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardContent from "@mui/material/CardContent";

const Review = (props) => {
  return (
    <ListItem alignItems="flex-start">
      <Card sx={{ width: "100%" }}>
        <CardHeader
          title={
            <Box
              sx={{
                display: "flex",
              }}
            >
              <Typography
                mr={"5%"}
              >{`${props.review.user.profile.firstName} ${props.review.user.profile.lastName}`}</Typography>
              <Rating value={props.review.rating} readOnly />
            </Box>
          }
        ></CardHeader>
        <CardContent>
          <Typography gutterBottom component="div">
            {props.review.title}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            {props.review.description}
          </Typography>
        </CardContent>
      </Card>
    </ListItem>
  );
};

export default Review;
