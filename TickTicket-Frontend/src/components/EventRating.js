import React from "react";
import Rating from "@mui/material/Rating";
import Typography from "@mui/material/Typography";

const EventRating = (props) => {
  return (
    <>
      {props.reviews.length === 0 ? (
        <Typography
          variant="body2"
          color="text.secondary"
          sx={{ marginLeft: "3%" }}
        >
          No reviews yet
        </Typography>
      ) : (
        <>
          <Typography
            sx={{ marginLeft: "3%", marginRight: "5px" }}
            color="text.secondary"
          >
            {props.rating}
          </Typography>
          <Rating name="read-only" value={props.rating} readOnly />
          <Typography sx={{ marginLeft: "5px" }} color="text.secondary">
            ({props.reviews.length})
          </Typography>
        </>
      )}
    </>
  );
};
export default EventRating;
