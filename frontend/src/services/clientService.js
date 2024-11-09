import httpClient from "../Utils/http-common";

export const postClient = (userData) => {
  const response = httpClient.post("auth/register", userData);
  return { data: response.data, status: response.status };
};
