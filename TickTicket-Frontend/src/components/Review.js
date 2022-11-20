import React from "react";
import axios from "axios";
import {
  Box,
  Card,
  CardContent,
  CardHeader,
  IconButton,
  ListItem,
  Rating,
  Typography,
} from "@mui/material";
import { Edit as EditIcon, Delete as DeleteIcon } from "@mui/icons-material";
import ReviewModal from "./ReviewModal";

const Review = (props) => {
  const [reviewOpen, setReviewOpen] = React.useState(false);

  const handleDelete = async () => {
    await axios.delete(`reviews/${props.review.id}`);
    props.loadData();
  };

  const handleReviewOpen = () => {
    setReviewOpen(true);
  };

  const handleReviewClose = () => setReviewOpen(false);

  const editReview = async (title, description, rating) => {
    await axios.patch(`reviews`, {
      title: title,
      description: description,
      rating: rating,
      id: props.review.id,
    });
    props.loadData();
    handleReviewClose();
  };

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
          action={
            props.review.user.id === localStorage.getItem("userId") && (
              <>
                <IconButton
                  size="large"
                  color="inherit"
                  onClick={handleReviewOpen}
                >
                  <EditIcon />
                </IconButton>
                <IconButton size="large" color="inherit" onClick={handleDelete}>
                  <DeleteIcon />
                </IconButton>
              </>
            )
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
      <ReviewModal
        open={reviewOpen}
        handleClose={handleReviewClose}
        handleAction={editReview}
        review={props.review}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      ></ReviewModal>
    </ListItem>
  );
};

export default Review;
