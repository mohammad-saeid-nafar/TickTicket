import React from "react";
import {
  Box,
  Card,
  CardHeader,
  Modal,
  Stack,
  Rating,
  Typography,
} from "@mui/material";
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
        <CardHeader
          title={
            <Box
              sx={{
                display: "flex",
              }}
            >
              <Typography>{props.reviews[0].event.name}</Typography>
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
            </Box>
          }
        />
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
