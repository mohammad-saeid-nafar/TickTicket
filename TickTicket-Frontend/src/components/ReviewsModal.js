import React from "react";
import { Card, Modal, Stack } from "@mui/material";
import Review from "./Review";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: "50%",
  bgcolor: "background.paper",
  boxShadow: 24,
  p: 4,
};

const ReviewsModal = (props) => {
  return (
    <Modal open={props.open} onClose={props.onClose}>
      <Card sx={style}>
        <Stack>
          {props.reviews.map((review) => (
            <Review key={review.id} review={review} loadData={props.loadData} />
          ))}
        </Stack>
      </Card>
    </Modal>
  );
};

export default ReviewsModal;
