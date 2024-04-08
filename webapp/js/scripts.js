$(document).ready(function () {/* jQuery toggle layout */
  $('#btnToggle').click(function () {
    if ($(this).hasClass('on')) {
      $('#main .col-md-6').addClass('col-md-4').removeClass('col-md-6');
      $(this).removeClass('on');
    } else {
      $('#main .col-md-4').addClass('col-md-6').removeClass('col-md-4');
      $(this).addClass('on');
    }
  });
});

$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();

  const queryString = $("form[name=answer]").serialize();

  $.ajax({
    type: "post",
    url: "/api/qna/addAnswer",
    data: queryString,
    dataType: "json",
    error: onError,
    success: onSuccess,
  })
}

function onSuccess(json, status) {
  const { answer } = json;
  const answerTemplate = $("#answerTemplate").html();
  const template = answerTemplate.format(
    answer.writer,
    new Date(answer.createdDate),
    answer.contents,
    answer.answerId);
  $(".qna-comment-slipp-articles").prepend(template);
}

function onError(xhr, status) {
  alert('error : failed to add answer');
}

$(".qna-comment").on("click", ".form-delete", deleteAnswer);

function deleteAnswer(e) {
  e.preventDefault();

  const deleteBtn = $(this);
  const queryString = deleteBtn.closest("form").serialize();

  $.ajax({
    type: "post",
    url: "/api/qna/deleteAnswer",
    data: queryString,
    dataType: "json",
    error: function (xhr, status) {
      alert("error : failed to delete answer");
    },
    success: function (json, status) {
      const { result } = json;
      if (result.success) {
        deleteBtn.closest("article").remove();
      }
    }
  });
}

String.prototype.format = function () {
  const args = arguments;
  return this.replace(/{(\d+)}/g, function (match, number) {
    return typeof args[number] != 'undefined'
      ? args[number]
      : match;
  });
};
