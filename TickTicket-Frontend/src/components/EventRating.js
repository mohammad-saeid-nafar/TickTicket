import React from "react";
import { useState } from "react";
import { Rating, Typography, Link } from "@mui/material";
import ReviewsModal from "./ReviewsModal";

const EventRating = (props) => {
  const [reviewsOpen, setReviewsOpen] = useState(false);

  const handleReviewOpen = () => {
    setReviewsOpen(true);
  };

  const handleReviewsClose = () => {
    console.log(reviewsOpen)
    setReviewsOpen(false)};

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
          <Link
            sx={{ marginLeft: "5px" }}
            component="button"
            variant="body2"
            onClick={handleReviewOpen}
          >
            View reviews
          </Link>
          <ReviewsModal reviews={props.reviews} open={reviewsOpen} onClose={handleReviewsClose} loadData={props.loadData}/>
        </>
      )}
    </>
  );
};
export default EventRating;
