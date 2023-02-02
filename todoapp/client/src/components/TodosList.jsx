import React, { useState, useEffect } from "react";
import TodoDataService from "../services/TodoService";
import { Link } from "react-router-dom";

const TodosList = () => {
  const [Todos, setTodos] = useState([]);
  const [currentTodo, setCurrentTodo] = useState(null);
  const [currentIndex, setCurrentIndex] = useState(-1);
  const [searchTitle, setSearchTitle] = useState("");

  useEffect(() => {
    retrieveTodos();
  }, []);

  const onChangeSearchTitle = e => {
    const searchTitle = e.target.value;
    setSearchTitle(searchTitle);
  };

  const retrieveTodos = () => {
    TodoDataService.getAll()
      .then(response => {
        setTodos(response.data);
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  };

  const refreshList = () => {
    retrieveTodos();
    setCurrentTodo(null);
    setCurrentIndex(-1);
  };

  const setActiveTodo = (Todo, index) => {
    setCurrentTodo(Todo);
    setCurrentIndex(index);
  };

  const removeAllTodos = () => {
    TodoDataService.removeAll()
      .then(response => {
        console.log(response.data);
        refreshList();
      })
      .catch(e => {
        console.log(e);
      });
  };

  const findByTitle = () => {
    TodoDataService.findByTitle(searchTitle)
      .then(response => {
        setTodos(response.data);
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  };

  return (
    <div className="list row">
      <div className="col-md-12">
        <div className="input-group mb-3">
          <input
            type="text"
            className="form-control"
            placeholder="Search by title"
            value={searchTitle}
            onChange={onChangeSearchTitle}
          />
          <div className="input-group-append">
            <button
              className="btn btn-outline-secondary"
              type="button"
              onClick={findByTitle}
            >
              Search
            </button>
          </div>
        </div>
      </div>
      <div className="col-md-6">
        <h4>Todos List</h4>

        <ul className="list-group">
          {Todos &&
            Todos.map((Todo, index) => (
              <li
                className={
                  "list-group-item " + (index === currentIndex ? "active" : "")
                }
                onClick={() => setActiveTodo(Todo, index)}
                key={index}
              >
                {Todo.title}
              </li>
            ))}
        </ul>

        <button
          className="m-3 btn btn-sm btn-danger"
          onClick={removeAllTodos}
        >
          Remove All
        </button>
      </div>
      <div className="col-md-6">
        {currentTodo ? (
          <div>
            <h4>Todo</h4>
            <div>
              <label>
                <strong>Title:</strong>
              </label>{" "}
              {currentTodo.title}
            </div>
            <div className="text-break text-wrap">
              <label>
                <strong>Description:</strong>
              </label>{" "}
              {currentTodo.description}
            </div>
            <div>
              <label>
                <strong>Status:</strong>
              </label>{" "}
              {currentTodo.published ? "Published" : "Pending"}
            </div>

            <Link
              to={"/Todos/" + currentTodo.id}
              className="bg-warning badge link-dark">
              Edit
            </Link>
          </div>
        ) : (
          <div>
            <br />
            <p>Please click on a Todo...</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default TodosList;