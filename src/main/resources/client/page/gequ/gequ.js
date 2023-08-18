const likeButton = document.getElementById("likeButton");

likeButton.addEventListener("click", function() {
  likeButton.querySelector(".heart-icon").classList.toggle("active");
  
  // 根据点赞状态执行相应的操作
  if (likeButton.querySelector(".heart-icon").classList.contains("active")) {
  // 执行点赞操作
  } else {
  // 执行取消点赞操作
  }
});