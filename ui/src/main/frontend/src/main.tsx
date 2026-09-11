import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Route, Routes } from "react-router";
import "./index.css";
import Layout from "./Layout.tsx";
import LoginPage from "./LoginPage.tsx";
import LogoutPage from "./LogoutPage.tsx";
import RegisterPage from "./RegisterPage.tsx";
import TodoListsPage from "./TodoListsPage.tsx";
import TodoListDetailsPage from "./TodoListDetailsPage.tsx";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route Component={Layout}>
          <Route index Component={TodoListsPage} />
          <Route path="/login" Component={LoginPage} />
          <Route path="/register" Component={RegisterPage} />
          <Route path="/logout" Component={LogoutPage} />
          <Route path="/todo/lists/:id" Component={TodoListDetailsPage} />
        </Route>
      </Routes>
    </BrowserRouter>
  </StrictMode>,
);
