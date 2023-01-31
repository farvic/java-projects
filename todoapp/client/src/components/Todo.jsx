import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from 'react-router-dom';
import todoDataService from "../services/todoService";

const todo = props => {
  const { id }= useParams();
  let navigate = useNavigate();

  const initialtodoState = {
    id: null,
    title: "",
    description: "",
    published: false
  };
  const [currenttodo, setCurrenttodo] = useState(initialtodoState);
  const [message, setMessage] = useState("");

  const gettodo = id => {
    todoDataService.get(id)
      .then(response => {
        setCurrenttodo(response.data);
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  };

  useEffect(() => {
    if (id)
      gettodo(id);
  }, [id]);

  const handleInputChange = event => {
    const { name, value } = event.target;
    setCurrenttodo({ ...currenttodo, [name]: value });
  };

  const updatePublished = status => {
    var data = {
      id: currenttodo.id,
      title: currenttodo.title,
      description: currenttodo.description,
      published: status
    };

    todoDataService.update(currenttodo.id, data)
      .then(response => {
        setCurrenttodo({ ...currenttodo, published: status });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  };

  const updatetodo = () => {
    todoDataService.update(currenttodo.id, currenttodo)
      .then(response => {
        console.log(response.data);
        setMessage("The todo was updated successfully!");
      })
      .catch(e => {
        console.log(e);
      });
  };

  const deletetodo = () => {
    todoDataService.remove(currenttodo.id)
      .then(response => {
        console.log(response.data);
        navigate("/todos");
      })
      .catch(e => {
        console.log(e);
      });
  };

  return (
    <div>
      {currenttodo ? (
        <div className="edit-form">
          <h4>todo</h4>
          <form>
            <div className="form-group">
              <label htmlFor="title">Title</label>
              <input
                type="text"
                className="form-control"
                id="title"
                name="title"
                value={currenttodo.title}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="description">Description</label>
              <input
                type="text"
                className="form-control"
                id="description"
                name="description"
                value={currenttodo.description}
                onChange={handleInputChange}
              />
            </div>

            <div className="form-group">
              <label>
                <strong>Status:</strong>
              </label>
              {currenttodo.published ? "Published" : "Pending"}
            </div>
          </form>
          {/* "bg-warning badge link-dark" */}
          {currenttodo.published ? (
            <button
              className="badge bg-primary mr-2"
              onClick={() => updatePublished(false)}
            >
              Unpublish
            </button>
          ) : (
            <button
              className="badge bg-primary mr-2"
              onClick={() => updatePublished(true)}
            >
              Publish
            </button>
          )}

          <button className="badge bg-danger mr-2" onClick={deletetodo}>
            Delete
          </button>

          <button
            type="submit"
            className="badge bg-success"
            onClick={updatetodo}
          >
            Update
          </button>
          <p>{message}</p>
        </div>
      ) : (
        <div>
          <br />
          <p>Please click on a todo...</p>
        </div>
      )}
    </div>
  );
};

export default todo;